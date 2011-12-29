OSTYPE := $(shell uname)
COMMON_CFLAGS = -static-libgcc
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++

BUILD_CONF = Release
CFLAGS = -O3
CDEFINES = -DBUILDING_RAW_LOADER

CPPFLAGS = $(CFLAGS)
CPPDEFINES = $(CDEFINES)

CC = gcc $(COMMON_CFLAGS) $(CFLAGS) $(CDEFINES)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS) $(CPPDEFINES)

LIBS = $(TARGET)/lib/raw.a $(TARGET)/lib/jpeg8c.dll.a $(TARGET)/lib/bitmaps.dll.a

INCLUDES = -I"../libraw/libraw" -I"../jpeg-8c/include" -I"../bitmaps/include"

SOURCE = src
HEADERS = include
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = raw_loader.o

HEADER_FILES = raw_loader.h

OBJECT_FILES_WITH_PATH = $(addprefix $(TARGET_OBJ)/,$(OBJECT_FILES))
HEADER_FILES_WITH_PATH = $(addprefix $(HEADERS)/,$(HEADER_FILES))

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows we need to link to libws2_32
	LIBRAW_LOADER_SHARED = raw.CatEyeLoader
	LIBS_ADD = -lws2_32
else											# any other case assumed as a POSIX system
	LIBRAW_LOADER_SHARED = raw.CatEyeLoader
	LIBS_ADD = 
endif

all: shared_lib

shared_lib: $(TARGET_BIN)/$(LIBRAW_LOADER_SHARED)

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

$(TARGET_BIN)/$(LIBRAW_LOADER_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) $(LIBS_ADD) -o $@

.PHONY: all shared_lib static_lib
.SILENT: