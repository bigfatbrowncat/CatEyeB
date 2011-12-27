OSTYPE := $(shell uname)
COMMON_CFLAGS = -static-libgcc
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++

BUILD_CONF = Release
CPPFLAGS = -O3

CC = gcc $(COMMON_CFLAGS) $(CFLAGS)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS)

LIBS = 
INCLUDES = $(SOURCE)/bitmaps.h

SOURCE = .
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = $(TARGET_OBJ)/PreciseBitmap.o		\
               $(TARGET_OBJ)/PreviewBitmap.o

ifneq (,$(findstring MINGW32, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows shared library is a dll
	LIBBITMAPS_SHARED = bitmaps.dll
else												# any other case assumed as a POSIX system
	LIBBITMAPS_SHARED = libbitmaps.so
endif

all: shared_lib

shared_lib: $(TARGET_BIN)/$(LIBBITMAPS_SHARED)

################### Folders ###################

$(TARGET_BIN):
	mkdir -p $(TARGET_BIN)

$(TARGET_OBJ):
	mkdir -p $(TARGET_OBJ)

$(TARGET_LIB):
	mkdir -p $(TARGET_LIB)

################### Objects ###################

$(TARGET_OBJ)/PreciseBitmap.o: $(SOURCE)/PreciseBitmap.cpp $(INCLUDES) $(TARGET_OBJ)
	$(CPP) -c $(SOURCE)/PreciseBitmap.cpp -o $@

$(TARGET_OBJ)/PreviewBitmap.o: $(SOURCE)/PreviewBitmap.cpp $(INCLUDES) $(TARGET_OBJ)
	$(CPP) -c $(SOURCE)/PreviewBitmap.cpp -o $@

################### Targets ###################

$(TARGET_BIN)/$(LIBBITMAPS_SHARED): $(OBJECT_FILES) $(TARGET_BIN) $(TARGET_LIB)
	$(CPP) -shared $(OBJECT_FILES) $(LIBS) -o $@ -Wl,--out-implib=$(TARGET_LIB)/$(LIBBITMAPS_SHARED).a
