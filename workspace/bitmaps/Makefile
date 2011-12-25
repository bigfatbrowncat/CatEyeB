OSTYPE := $(shell uname)
COMMON_CPPFLAGS = -static-libgcc -static-libstdc++
SRC_PATH = .

target_bin_R = bin/Release
OBJECT_PATH_R = $(target_bin_R)/obj
OBJECT_FILES_R = $(OBJECT_PATH_R)/FloatBitmap.o		\
                 $(OBJECT_PATH_R)/Int8Bitmap.o		\
                 $(OBJECT_PATH_R)/Int16Bitmap.o
R_FLAGS = -O4


target_bin_D = bin/Debug
OBJECT_PATH_D = $(target_bin_D)/obj
OBJECT_FILES_D = $(OBJECT_PATH_D)/FloatBitmap.o		\
                 $(OBJECT_PATH_R)/Int8Bitmap.o		\
                 $(OBJECT_PATH_R)/Int16Bitmap.o
D_FLAGS = -O0 -g3


ifneq (,$(findstring MINGW32, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows shared library is a dll
	libbitmaps_shared = bitmaps.dll
else												# any other case assumed as a POSIX system
	libbitmaps_shared = libbitmaps.so
endif

all: debug release

debug: $(target_bin_D)/$(libbitmaps_shared)

release: $(target_bin_R)/$(libbitmaps_shared)

################### Folders ###################

$(target_bin_D):
	mkdir -p $(target_bin_D)

$(target_bin_R):
	mkdir -p $(target_bin_R)

$(OBJECT_PATH_D):
	mkdir -p $(OBJECT_PATH_D)

$(OBJECT_PATH_R):
	mkdir -p $(OBJECT_PATH_R)
	
################### Objects ###################

# Debug

$(OBJECT_PATH_D)/FloatBitmap.o: $(SRC_PATH)/FloatBitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_D)
	g++ -c $(SRC_PATH)/FloatBitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(D_FLAGS)

$(OBJECT_PATH_D)/Int16Bitmap.o: $(SRC_PATH)/Int16Bitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_D)
	g++ -c $(SRC_PATH)/Int16Bitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(D_FLAGS)

$(OBJECT_PATH_D)/Int8Bitmap.o: $(SRC_PATH)/Int8Bitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_D)
	g++ -c $(SRC_PATH)/Int8Bitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(D_FLAGS)

# Release

$(OBJECT_PATH_R)/FloatBitmap.o: $(SRC_PATH)/FloatBitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_R)
	g++ -c $(SRC_PATH)/FloatBitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(R_FLAGS)

$(OBJECT_PATH_R)/Int16Bitmap.o: $(SRC_PATH)/Int16Bitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_R)
	g++ -c $(SRC_PATH)/Int16Bitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(R_FLAGS)

$(OBJECT_PATH_R)/Int8Bitmap.o: $(SRC_PATH)/Int8Bitmap.cpp $(SRC_PATH)/bitmaps.h $(OBJECT_PATH_R)
	g++ -c $(SRC_PATH)/Int8Bitmap.cpp -o $@ $(COMMON_CPPFLAGS) $(R_FLAGS)

################### Targets ###################

$(target_bin_D)/$(libbitmaps_shared): $(OBJECT_FILES_D) $(target_bin_D)
	g++ -shared $(OBJECT_FILES_D) -o $@ $(COMMON_CPPFLAGS) $(D_FLAGS) $(LIBS) -Wl,--out-implib=$@.a

$(target_bin_R)/$(libbitmaps_shared): $(OBJECT_FILES_R) $(target_bin_R)
	g++ -shared $(OBJECT_FILES_R) -o $@ $(COMMON_CPPFLAGS) $(R_FLAGS) $(LIBS) -Wl,--out-implib=$@.a # -Wl,--export-all-symbols
	