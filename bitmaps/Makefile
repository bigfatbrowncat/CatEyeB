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

SOURCE = src
HEADERS = include
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = PreciseBitmap.o PreviewBitmap.o

HEADER_FILES = bitmaps.h

OBJECT_FILES_WITH_PATH = $(addprefix $(TARGET_OBJ)/,$(OBJECT_FILES))
HEADER_FILES_WITH_PATH = $(addprefix $(HEADERS)/,$(HEADER_FILES))

LIBBITMAPS_STATIC = bitmaps.a

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows we need to link to libws2_32
	LIBBITMAPS_SHARED = bitmaps.dll
else											# any other case assumed as a POSIX system
	LIBBITMAPS_SHARED = libbitmaps.so
endif

all: shared_lib static_lib

static_lib: $(TARGET_LIB)/$(LIBBITMAPS_STATIC)
shared_lib: $(TARGET_BIN)/$(LIBBITMAPS_SHARED)

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