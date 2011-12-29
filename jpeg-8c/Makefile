OSTYPE := $(shell uname)
COMMON_CFLAGS = -static-libgcc
COMMON_CPPFLAGS = $(COMMON_CFLAGS) -static-libstdc++

BUILD_CONF = Release
CFLAGS = -O3

CC = gcc $(COMMON_CFLAGS) $(CFLAGS)
CPP = g++ $(COMMON_CPPFLAGS) $(CPPFLAGS)

LIBS = 
INCLUDES =

SOURCE = src
HEADERS = include
TARGET = ../target/$(BUILD_CONF)

TARGET_BIN = $(TARGET)/bin
TARGET_OBJ = $(TARGET)/obj
TARGET_LIB = $(TARGET)/lib

OBJECT_FILES = jaricom.o jcapimin.o jcapistd.o jcarith.o jccoefct.o jccolor.o \
               jcdctmgr.o jchuff.o jcinit.o jcmainct.o jcmarker.o jcmaster.o \
               jcomapi.o jcparam.o jcprepct.o jcsample.o jctrans.o jdapimin.o \
               jdapistd.o jdarith.o jdatadst.o jdatasrc.o jdcoefct.o jdcolor.o \
               jddctmgr.o jdhuff.o jdinput.o jdmainct.o jdmarker.o jdmaster.o \
               jdmerge.o jdpostct.o jdsample.o jdtrans.o jerror.o jfdctflt.o \
               jfdctfst.o jfdctint.o jidctflt.o jidctfst.o jidctint.o jquant1.o \
               jquant2.o jutils.o jmemmgr.o jmemnobs.o

HEADER_FILES = jconfig.h jdct.h jerror.h jinclude.h jmemsys.h                  \
               jmorecfg.h jpegint.h jpeglib.h jversion.h transupp.h            

OBJECT_FILES_WITH_PATH = $(addprefix $(TARGET_OBJ)/,$(OBJECT_FILES))
HEADER_FILES_WITH_PATH = $(addprefix $(HEADERS)/,$(HEADER_FILES))

LIBJPEG_STATIC = jpeg8c.a

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows we need to link to libws2_32
	LIBJPEG_SHARED = jpeg8c.dll
else											# any other case assumed as a POSIX system
	LIBJPEG_SHARED = libjpeg8c.so
endif

all: shared_lib static_lib

static_lib: $(TARGET_LIB)/$(LIBJPEG_STATIC)
shared_lib: $(TARGET_BIN)/$(LIBJPEG_SHARED)

################### Folders ###################

ENSURE_BIN = if [ ! -d "$(TARGET_BIN)" ]; then mkdir -p "$(TARGET_BIN)"; fi
ENSURE_LIB = if [ ! -d "$(TARGET_LIB)" ]; then mkdir -p "$(TARGET_LIB)"; fi
ENSURE_OBJ = if [ ! -d "$(TARGET_OBJ)" ]; then mkdir -p "$(TARGET_OBJ)"; fi

################### Objects ###################

$(OBJECT_FILES_WITH_PATH) : $(TARGET_OBJ)/%.o : $(SOURCE)/%.c $(HEADER_FILES_WITH_PATH)
	@echo "Compiling $@ ..."
	$(ENSURE_OBJ)
	$(CC) -c $< -o $@ $(INCLUDES) -I"$(HEADERS)"

################### Targets ###################

$(TARGET_BIN)/$(LIBJPEG_SHARED): $(OBJECT_FILES_WITH_PATH)
	@echo "Building shared library $@ ..."
	$(ENSURE_BIN)
	$(ENSURE_LIB)
	$(CC) -shared $(OBJECT_FILES_WITH_PATH) $(LIBS) -o $@ -Wl,--out-implib=$(TARGET_LIB)/$(LIBJPEG_SHARED).a

$(TARGET_LIB)/$(LIBJPEG_STATIC): $(OBJECT_FILES_WITH_PATH)
	@echo "Building static library $@ ..."
	$(ENSURE_LIB)
	ar crv $@ $(OBJECT_FILES_WITH_PATH)
	ranlib $@

.PHONY: all shared_lib static_lib
.SILENT:

# ===== Samples =====

# Executable sources & libs
#cjpeg_SRC = samples/cjpeg
#cjpeg_SOURCES = $(cjpeg_SRC)/cjpeg.c $(cjpeg_SRC)/rdppm.c $(cjpeg_SRC)/rdgif.c $(cjpeg_SRC)/rdtarga.c $(cjpeg_SRC)/rdrle.c $(cjpeg_SRC)/rdbmp.c \
#                $(cjpeg_SRC)/rdswitch.c $(cjpeg_SRC)/cdjpeg.c

#djpeg_SRC = samples/djpeg
#djpeg_SOURCES = $(djpeg_SRC)/djpeg.c $(djpeg_SRC)/wrppm.c $(djpeg_SRC)/wrgif.c $(djpeg_SRC)/wrtarga.c $(djpeg_SRC)/wrrle.c $(djpeg_SRC)/wrbmp.c \
#                $(djpeg_SRC)/rdcolmap.c $(djpeg_SRC)/cdjpeg.c

#jpegtran_SRC = samples/jpegtran
#jpegtran_SOURCES = $(jpegtran_SRC)/jpegtran.c $(jpegtran_SRC)/rdswitch.c $(jpegtran_SRC)/cdjpeg.c $(jpegtran_SRC)/transupp.c

#rdjpgcom_SOURCES = $(SOURCE)/rdjpgcom.c
#wrjpgcom_SOURCES = $(SOURCE)/wrjpgcom.c


#samples: $(samples_OUT)/cjpeg $(samples_OUT)/djpeg $(samples_OUT)/jpegtran $(samples_OUT)/rdjpgcom $(samples_OUT)/wrjpgcom

#$(samples_OUT):
#	mkdir -p $@	

#$(samples_OUT)/cjpeg: $(cjpeg_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
#	g++ -static $(cjpeg_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)
	
#$(samples_OUT)/djpeg: $(djpeg_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
#	g++ -static $(djpeg_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

#$(samples_OUT)/jpegtran: $(jpegtran_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
#	g++ -static $(jpegtran_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

#$(samples_OUT)/rdjpgcom: $(rdjpgcom_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
#	g++ -static $(rdjpgcom_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

#$(samples_OUT)/wrjpgcom: $(wrjpgcom_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
#	g++ -static $(wrjpgcom_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)
	