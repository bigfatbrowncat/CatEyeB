OSTYPE := $(shell uname)
COMMON_CFLAGS = -static-libgcc
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++

BUILD_CONF = Release
CFLAGS = -O3
CDEFINES = -DBUILDING_BRIGHTNESS_OPERATION

CPPFLAGS = $(CFLAGS)
CPPDEFINES = $(CDEFINES)

CC = gcc $(COMMON_CFLAGS) $(CFLAGS) $(CDEFINES)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS) $(CPPDEFINES)

LIBS = $(TARGET)/lib/bitmaps.dll.a

INCLUDES = -I"../bitmaps/include"

SOURCE = src
HEADERS = include
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = brightness_operation.o

HEADER_FILES = brightness_operation.h

OBJECT_FILES_WITH_PATH = $(addprefix $(TARGET_OBJ)/,$(OBJECT_FILES))
HEADER_FILES_WITH_PATH = $(addprefix $(HEADERS)/,$(HEADER_FILES))

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	LIBBRIGHTNESS_OPERATION_SHARED = brightness.CatEyeOperation
	LIBS_ADD = -lm
else											# any other case assumed as a POSIX system
	LIBBRIGHTNESS_OPERATION_SHARED = brightness.CatEyeOperation
	LIBS_ADD = -lm
endif

all: shared_lib

shared_lib: $(TARGET_BIN)/$(LIBBRIGHTNESS_OPERATION_SHARED)

################### Folders ###################

ENSURE_BIN = if [ ! -d "$(TARGET_BIN)" ]; then mkdir -p "$(TARGET_BIN)"; fi
ENSURE_LIB = if [ ! -d "$(TARGET_LIB)" ]; then mkdir -p "$(TARGET_LIB)"; fi
ENSURE_OBJ = if [ ! -d "$(TARGET_OBJ)" ]; then mkdir -p "$(TARGET_OBJ)"; fi

################### Objects ###################

$(OBJECT_FILES_WITH_PATH) : $(TARGET_OBJ)/%.o : $(SOURCE)/%.cpp $(HEADER_FILES_WITH_PATH)
	@echo "Compiling $@ ..."
	$(ENSURE_OBJ)
	$(CPP) -c $< -o $@ $(INCLUDES) -I"$(HEADERS)"

################### Targets ###################

$(TARGET_BIN)/$(LIBBRIGHTNESS_OPERATION_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) $(LIBS_ADD) -o $@

.PHONY: all shared_lib
.SILENT: