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
INCLUDES =

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

shared_lib: $(TARGET_BIN)/$(LIBBITMAPS_SHARED)

################### Folders ###################

ENSURE_BIN = if [ ! -d "$(TARGET_BIN)" ]; then mkdir -p "$(TARGET_BIN)"; fi
ENSURE_GEN = if [ ! -d "$(TARGET_GEN)" ]; then mkdir -p "$(TARGET_GEN)"; fi
ENSURE_OBJ = if [ ! -d "$(TARGET_OBJ)" ]; then mkdir -p "$(TARGET_OBJ)"; fi

################### Objects ###################

$(TARGET_GEN)/com/cateye/core/native_/PreciseBitmap.h : $(SOURCE)/com/cateye/core/native_/PreciseBitmap.java
	@echo "Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -classpath $(TARGET_BIN) com.cateye.core.native_.PreciseBitmap

$(TARGET_GEN)/com/cateye/core/native_/PreviewBitmap.h : $(SOURCE)/com/cateye/core/native_/PreviewBitmap.java
	@echo "Generating $@ ..."
	if [ ! -d "$(TARGET_GEN)/com/cateye/core/native_" ]; then mkdir -p "$(TARGET_GEN)/com/cateye/core/native_"; fi
	"$(JAVA_HOME)/bin/javah" -classpath $(TARGET_BIN) com.cateye.core.native_.PreviewBitmap

$(OBJECT_FILES_WITH_PATH) : $(TARGET_OBJ)/%.o : $(SOURCE)/%.cpp $(TARGET_GEN)/%.h 
	@echo "Compiling $@ ..."
	$(ENSURE_OBJ)
	$(CPP) -c $< -o $@ -I"$(TARGET_GEN)"

################### Targets ###################

$(TARGET_BIN)/$(LIBBITMAPS_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) -o $@ -Wl,--out-implib=$(TARGET_LIB)/$(LIBBITMAPS_SHARED).a

$(TARGET_LIB)/$(LIBBITMAPS_STATIC): $(OBJECT_FILES_WITH_PATH)
	@echo "Building static library $@ ..."
	$(ENSURE_LIB)
	ar crv $@ $(OBJECT_FILES_WITH_PATH)
	ranlib $@

.PHONY: all shared_lib static_lib
.SILENT: