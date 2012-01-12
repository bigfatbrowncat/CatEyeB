OSTYPE := $(shell uname)

BUILD_CONF = Release

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

LIBS = 
INCLUDES = -I"$(JAVA_HOME)/include" -I"$(JAVA_HOME)/include/win32" 

SOURCE = .
TARGET = .

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_GEN = $(TARGET)/gen

OBJECTS = com/cateye/core/native_/PreciseBitmap \
          com/cateye/core/native_/PreviewBitmap


OBJECT_FILES_WITH_PATH = $(addsuffix .o,$(addprefix $(TARGET_OBJ)/,$(OBJECTS)))
JNI_HEADERS_WITH_PATH = $(addsuffix .h,$(addprefix $(TARGET_GEN)/,$(OBJECTS)))

LIBBITMAPS_SHARED = CatEyeB.Core.native

all: shared_lib
clean:
	rm -rf $(TARGET_GEN)
	rm $(TARGET_BIN)/$(LIBBITMAPS_SHARED)
	rm $(OBJECT_FILES_WITH_PATH)
	rm $(JNI_HEADERS_WITH_PATH)

shared_lib: $(TARGET_BIN)/$(LIBBITMAPS_SHARED)

################### Folders ###################

ENSURE_BIN = if [ ! -d "$(TARGET_BIN)" ]; then mkdir -p "$(TARGET_BIN)"; fi
ENSURE_GEN = if [ ! -d "$(TARGET_GEN)" ]; then mkdir -p "$(TARGET_GEN)"; fi
ENSURE_OBJ = if [ ! -d "$(TARGET_OBJ)" ]; then mkdir -p "$(TARGET_OBJ)"; fi

################ Java classes #################

classes:
	@echo "Compiling java classes ..."
	$(ENSURE_BIN)
	"$(JAVA_HOME)/bin/javac" com/cateye/core/*.java -d bin 
	"$(JAVA_HOME)/bin/javac" com/cateye/core/native_/*.java -d bin 

################# JNI Headers #################

$(TARGET_GEN)/com/cateye/core/native_/PreciseBitmap.h : $(SOURCE)/com/cateye/core/native_/PreciseBitmap.java classes
	@echo "Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -o $(TARGET_GEN)/com/cateye/core/native_/PreciseBitmap.h com.cateye.core.native_.PreciseBitmap

$(TARGET_GEN)/com/cateye/core/native_/PreviewBitmap.h : $(SOURCE)/com/cateye/core/native_/PreviewBitmap.java classes
	@echo "Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -o $(TARGET_GEN)/com/cateye/core/native_/PreviewBitmap.h com.cateye.core.native_.PreviewBitmap

################### Objects ###################

$(OBJECT_FILES_WITH_PATH) : $(TARGET_OBJ)/%.o : $(SOURCE)/%.cpp $(TARGET_GEN)/%.h 
	@echo "Compiling $@ ..."
	if [ ! -d "$(TARGET_OBJ)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_OBJ)/com/cateye/core/native_"; fi
	$(CPP) -c $< -o $@ -I"$(TARGET_GEN)" $(INCLUDES)

################### Targets ###################

$(TARGET_BIN)/$(LIBBITMAPS_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) -o $@ -Wl,--add-stdcall-alias

.PHONY: all shared_lib clean
.SILENT: