OSTYPE := $(shell uname)

PROJ = CatEyeB.Core

# CC macros
COMMON_CFLAGS = -static-libgcc
CFLAGS = -O3
CDEFINES = -DBUILDING_LIBBITMAP
CC = gcc $(COMMON_CFLAGS) $(CFLAGS) $(CDEFINES)

# CPP macros
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++
CPPFLAGS = $(CFLAGS)
CPPDEFINES = $(CDEFINES)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS) $(CPPDEFINES)

LIBS = $(TARGET_LIB)/bitmaps.dll.a
INCLUDES = -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" -I"../bitmaps/include"

SOURCE = .
TARGET = ../target

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib
TARGET_GEN = $(TARGET)/gen

OBJECTS = com/cateye/core/native_/PreciseBitmap \
          com/cateye/core/native_/PreviewBitmap

EXTERNAL_JARS = $(shell find ../ExternalLibs/lib -name \*.jar -printf "%p;")

JAVA_FILES := $(shell find . -name \*.java)
CLASS_FILES = $(addprefix $(TARGET_BIN)/,$(addsuffix .class,$(basename $(JAVA_FILES))))

OBJECT_FILES_WITH_PATH = $(addsuffix .o,$(addprefix $(TARGET_OBJ)/,$(OBJECTS)))
JNI_HEADERS_WITH_PATH = $(addsuffix .h,$(addprefix $(TARGET_GEN)/,$(OBJECTS)))

LIBBITMAPS_SHARED = CatEyeB.Core.native

all: shared_lib classes

clean:
	@echo "[$(PROJ) Cleaning...]"
	rm -rf $(TARGET_GEN)
	rm -rf $(TARGET_BIN)
	rm -rf $(TARGET_OBJ)

shared_lib: $(TARGET_BIN)/$(LIBBITMAPS_SHARED)

################### Folders ###################

ENSURE_BIN = if [ ! -d "$(TARGET_BIN)" ]; then mkdir -p "$(TARGET_BIN)"; fi
ENSURE_GEN = if [ ! -d "$(TARGET_GEN)" ]; then mkdir -p "$(TARGET_GEN)"; fi
ENSURE_OBJ = if [ ! -d "$(TARGET_OBJ)" ]; then mkdir -p "$(TARGET_OBJ)"; fi

################ Java classes #################

classes: $(CLASS_FILES)

$(TARGET_BIN)/%.class : %.java
	@echo "[$(PROJ)] Compiling java class $@ ..."
	if [ ! -d "$(TARGET_BIN)/com/cateye/core" ]; then mkdir -p "$(TARGET_BIN)/com/cateye/core"; fi
	"$(JAVA_HOME)/bin/javac" -classpath "$(EXTERNAL_JARS)" -d "$(TARGET_BIN)" $<

################# JNI Headers #################

$(TARGET_GEN)/com/cateye/core/native_/PreciseBitmap.h : $(SOURCE)/com/cateye/core/native_/PreciseBitmap.java $(TARGET_BIN)/com/cateye/core/native_/PreciseBitmap.class
	@echo "[$(PROJ)] Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -classpath $(TARGET_BIN) -o $(TARGET_GEN)/com/cateye/core/native_/PreciseBitmap.h com.cateye.core.native_.PreciseBitmap

$(TARGET_GEN)/com/cateye/core/native_/PreviewBitmap.h : $(SOURCE)/com/cateye/core/native_/PreviewBitmap.java $(TARGET_BIN)/com/cateye/core/native_/PreviewBitmap.class
	@echo "[$(PROJ)] Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -classpath $(TARGET_BIN) -o $(TARGET_GEN)/com/cateye/core/native_/PreviewBitmap.h com.cateye.core.native_.PreviewBitmap

################### Objects ###################

$(OBJECT_FILES_WITH_PATH) : $(TARGET_OBJ)/%.o : $(SOURCE)/%.cpp $(TARGET_GEN)/%.h 
	@echo "[$(PROJ)] Compiling $@ ..."
	if [ ! -d "$(TARGET_OBJ)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_OBJ)/com/cateye/core/native_"; fi
	$(CPP) -c $< -o $@ -I"$(TARGET_GEN)" $(INCLUDES)

################### Targets ###################

$(TARGET_BIN)/$(LIBBITMAPS_SHARED): $(OBJECT_FILES_WITH_PATH) deplibs
	@echo "[$(PROJ)] Building native part: $@ ..."
	$(ENSURE_BIN)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) -o $@ -Wl,--add-stdcall-alias

############### Dependent libs ################

deplibs: $(TARGET_BIN)/bitmaps.dll

$(TARGET_BIN)/bitmaps.dll: ../bitmaps/Makefile
	@echo [$(PROJ)] $@ needed. Making it ...
	make --directory=../bitmaps	

.PHONY: all classes shared_lib deplibs clean
.SILENT: