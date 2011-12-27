OSTYPE := $(shell uname)
COMMON_CPPFLAGS = -static-libgcc -static-libstdc++
SRC_PATH = .

# TODO: should be an outer variable
BUILD_CONF = Release

FLAGS = -O4				# TODO: should be an outer variable
LIBS = 
# for debug use:  FLAGS = -O0 -g3

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

# Release

$(TARGET_OBJ)/PreciseBitmap.o: $(SRC_PATH)/PreciseBitmap.cpp $(SRC_PATH)/bitmaps.h $(TARGET_OBJ)
	g++ -c $(SRC_PATH)/PreciseBitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(FLAGS)

$(TARGET_OBJ)/PreviewBitmap.o: $(SRC_PATH)/PreviewBitmap.cpp $(SRC_PATH)/bitmaps.h $(TARGET_OBJ)
	g++ -c $(SRC_PATH)/PreviewBitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(FLAGS)

################### Targets ###################

$(TARGET_BIN)/$(LIBBITMAPS_SHARED): $(OBJECT_FILES) $(TARGET_BIN)
	g++ -shared $(OBJECT_FILES) -o $@ $(COMMON_CPPFLAGS) $(FLAGS) -Wl,--out-implib=$(TARGET_LIB)/$(LIBBITMAPS_SHARED).a
	