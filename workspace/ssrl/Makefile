OSTYPE := $(shell uname)
COMMON_CFLAGS = -static-libgcc
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++

BUILD_CONF = Release
CFLAGS = -O3

CC = gcc $(COMMON_CFLAGS) $(CFLAGS)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS)

LIBS = $(TARGET)/lib/raw.a $(TARGET)/lib/jpeg8c.dll.a $(TARGET)/lib/bitmaps.dll.a

INCLUDES = -I"include/libraw" -I"include/jpeg-8c" -I"include/bitmaps"

SOURCE = src
HEADERS = include
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = ssrl.o

HEADER_FILES = ssrl.h

OBJECT_FILES_WITH_PATH = $(addprefix $(TARGET_OBJ)/,$(OBJECT_FILES))
HEADER_FILES_WITH_PATH = $(addprefix $(HEADERS)/,$(HEADER_FILES))

LIBSSRL_STATIC = ssrl.a

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows we need to link to libws2_32
	LIBSSRL_SHARED = ssrl.dll
	LIBS_ADD = -lws2_32 -lm
else											# any other case assumed as a POSIX system
	LIBSSRL_SHARED = libssrl.so
	LIBS_ADD = 
endif

all: shared_lib static_lib

static_lib: $(TARGET_LIB)/$(LIBSSRL_STATIC)
shared_lib: $(TARGET_BIN)/$(LIBSSRL_SHARED)

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

$(TARGET_BIN)/$(LIBSSRL_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CPP) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) $(LIBS_ADD) -o $@ -Wl,--out-implib=$(TARGET_LIB)/$(LIBSSRL_SHARED).a

$(TARGET_LIB)/$(LIBSSRL_STATIC): $(OBJECT_FILES_WITH_PATH)
	@echo "Building static library $@ ..."
	$(ENSURE_LIB)
	ar crv $@ $(OBJECT_FILES_WITH_PATH)
	ranlib $@

.PHONY: all shared_lib static_lib
.SILENT: