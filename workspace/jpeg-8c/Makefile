OSTYPE := $(shell uname)

CPPFLAGS = -I"$(PWD)/include" -O4
LIBS = 

libjpeg_OUT = lib

objects_OUT = obj

samples_OUT = bin

libjpeg_SOURCES = jaricom.c jcapimin.c jcapistd.c jcarith.c jccoefct.c jccolor.c \
                  jcdctmgr.c jchuff.c jcinit.c jcmainct.c jcmarker.c jcmaster.c \
                  jcomapi.c jcparam.c jcprepct.c jcsample.c jctrans.c jdapimin.c \
                  jdapistd.c jdarith.c jdatadst.c jdatasrc.c jdcoefct.c jdcolor.c \
                  jddctmgr.c jdhuff.c jdinput.c jdmainct.c jdmarker.c jdmaster.c \
                  jdmerge.c jdpostct.c jdsample.c jdtrans.c jerror.c jfdctflt.c \
                  jfdctfst.c jfdctint.c jidctflt.c jidctfst.c jidctint.c jquant1.c \
                  jquant2.c jutils.c jmemmgr.c jmemnobs.c



libjpeg_OBJECTS = $(objects_OUT)/jaricom.o $(objects_OUT)/jcapimin.o $(objects_OUT)/jcapistd.o $(objects_OUT)/jcarith.o $(objects_OUT)/jccoefct.o $(objects_OUT)/jccolor.o \
                  $(objects_OUT)/jcdctmgr.o $(objects_OUT)/jchuff.o $(objects_OUT)/jcinit.o $(objects_OUT)/jcmainct.o $(objects_OUT)/jcmarker.o $(objects_OUT)/jcmaster.o \
                  $(objects_OUT)/jcomapi.o $(objects_OUT)/jcparam.o $(objects_OUT)/jcprepct.o $(objects_OUT)/jcsample.o $(objects_OUT)/jctrans.o $(objects_OUT)/jdapimin.o \
                  $(objects_OUT)/jdapistd.o $(objects_OUT)/jdarith.o $(objects_OUT)/jdatadst.o $(objects_OUT)/jdatasrc.o $(objects_OUT)/jdcoefct.o $(objects_OUT)/jdcolor.o \
                  $(objects_OUT)/jddctmgr.o $(objects_OUT)/jdhuff.o $(objects_OUT)/jdinput.o $(objects_OUT)/jdmainct.o $(objects_OUT)/jdmarker.o $(objects_OUT)/jdmaster.o \
                  $(objects_OUT)/jdmerge.o $(objects_OUT)/jdpostct.o $(objects_OUT)/jdsample.o $(objects_OUT)/jdtrans.o $(objects_OUT)/jerror.o $(objects_OUT)/jfdctflt.o \
                  $(objects_OUT)/jfdctfst.o $(objects_OUT)/jfdctint.o $(objects_OUT)/jidctflt.o $(objects_OUT)/jidctfst.o $(objects_OUT)/jidctint.o $(objects_OUT)/jquant1.o \
                  $(objects_OUT)/jquant2.o $(objects_OUT)/jutils.o $(objects_OUT)/jmemmgr.o $(objects_OUT)/jmemnobs.o

ifneq (,$(findstring MINGW, $(OSTYPE)))			# OS type is MinGW 32
	# In Windows we need to link to libws2_32
	libjpeg_shared = jpeg8c.dll
else											# any other case assumed as a POSIX system
|	libjpeg_shared = libjpeg8c.so
endif

all: library samples


# ===== Clean =====

clean: 
	rm -rf $(libjpeg_OUT)
	rm -rf $(objects_OUT)
	rm -rf $(samples_OUT)

# ===== Library =====

library: $(libjpeg_OUT)/libjpeg8c.a $(libjpeg_OUT)/$(libjpeg_shared)

$(libjpeg_OUT):
	mkdir -p $@

$(objects_OUT):
	mkdir -p $@
	
$(libjpeg_OUT)/$(libjpeg_shared): $(libjpeg_OBJECTS) $(libjpeg_OUT)
	gcc -shared -o $@ $(libjpeg_OBJECTS) $(LIBS)

$(libjpeg_OUT)/libjpeg8c.a: $(libjpeg_OBJECTS) $(libjpeg_OUT)
	ar crv $@ $(libjpeg_OBJECTS)
	ranlib $(libjpeg_OUT)/libjpeg8c.a
	          
$(objects_OUT)/jaricom.o: src/jaricom.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jaricom.c -o $@

$(objects_OUT)/jcapimin.o: src/jcapimin.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcapimin.c -o $@

$(objects_OUT)/jcapistd.o: src/jcapistd.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcapistd.c -o $@

$(objects_OUT)/jcarith.o: src/jcarith.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcarith.c -o $@

$(objects_OUT)/jccoefct.o: src/jccoefct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jccoefct.c -o $@

$(objects_OUT)/jccolor.o: src/jccolor.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jccolor.c -o $@

$(objects_OUT)/jcdctmgr.o: src/jcdctmgr.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcdctmgr.c -o $@

$(objects_OUT)/jchuff.o: src/jchuff.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jchuff.c -o $@

$(objects_OUT)/jcinit.o: src/jcinit.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcinit.c -o $@

$(objects_OUT)/jcmainct.o: src/jcmainct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcmainct.c -o $@

$(objects_OUT)/jcmarker.o: src/jcmarker.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcmarker.c -o $@

$(objects_OUT)/jcmaster.o: src/jcmaster.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcmaster.c -o $@

$(objects_OUT)/jcomapi.o: src/jcomapi.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcomapi.c -o $@

$(objects_OUT)/jcparam.o: src/jcparam.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcparam.c -o $@

$(objects_OUT)/jcprepct.o: src/jcprepct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcprepct.c -o $@

$(objects_OUT)/jcsample.o: src/jcsample.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jcsample.c -o $@

$(objects_OUT)/jctrans.o: src/jctrans.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jctrans.c -o $@

$(objects_OUT)/jdapimin.o: src/jdapimin.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdapimin.c -o $@

$(objects_OUT)/jdapistd.o: src/jdapistd.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdapistd.c -o $@

$(objects_OUT)/jdarith.o: src/jdarith.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdarith.c -o $@

$(objects_OUT)/jdatadst.o: src/jdatadst.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdatadst.c -o $@

$(objects_OUT)/jdatasrc.o: src/jdatasrc.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdatasrc.c -o $@

$(objects_OUT)/jdcoefct.o: src/jdcoefct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdcoefct.c -o $@

$(objects_OUT)/jdcolor.o: src/jdcolor.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdcolor.c -o $@

$(objects_OUT)/jddctmgr.o: src/jddctmgr.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jddctmgr.c -o $@

$(objects_OUT)/jdhuff.o: src/jdhuff.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdhuff.c -o $@

$(objects_OUT)/jdinput.o: src/jdinput.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdinput.c -o $@

$(objects_OUT)/jdmainct.o: src/jdmainct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdmainct.c -o $@

$(objects_OUT)/jdmarker.o: src/jdmarker.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdmarker.c -o $@

$(objects_OUT)/jdmaster.o: src/jdmaster.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdmaster.c -o $@

$(objects_OUT)/jdmerge.o: src/jdmerge.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdmerge.c -o $@

$(objects_OUT)/jdpostct.o: src/jdpostct.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdpostct.c -o $@

$(objects_OUT)/jdsample.o: src/jdsample.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdsample.c -o $@

$(objects_OUT)/jdtrans.o: src/jdtrans.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jdtrans.c -o $@

$(objects_OUT)/jerror.o: src/jerror.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jerror.c -o $@

$(objects_OUT)/jfdctflt.o: src/jfdctflt.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jfdctflt.c -o $@

$(objects_OUT)/jfdctfst.o: src/jfdctfst.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jfdctfst.c -o $@

$(objects_OUT)/jfdctint.o: src/jfdctint.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jfdctint.c -o $@

$(objects_OUT)/jidctflt.o: src/jidctflt.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jidctflt.c -o $@

$(objects_OUT)/jidctfst.o: src/jidctfst.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jidctfst.c -o $@

$(objects_OUT)/jidctint.o: src/jidctint.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jidctint.c -o $@

$(objects_OUT)/jquant1.o: src/jquant1.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jquant1.c -o $@

$(objects_OUT)/jquant2.o: src/jquant2.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jquant2.c -o $@

$(objects_OUT)/jutils.o: src/jutils.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jutils.c -o $@

$(objects_OUT)/jmemmgr.o: src/jmemmgr.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jmemmgr.c -o $@

$(objects_OUT)/jmemnobs.o: src/jmemnobs.c $(objects_OUT)
	gcc -c $(CPPFLAGS) src/jmemnobs.c -o $@

# ===== Samples =====

# Executable sources & libs
cjpeg_SRC = samples/cjpeg
cjpeg_SOURCES = $(cjpeg_SRC)/cjpeg.c $(cjpeg_SRC)/rdppm.c $(cjpeg_SRC)/rdgif.c $(cjpeg_SRC)/rdtarga.c $(cjpeg_SRC)/rdrle.c $(cjpeg_SRC)/rdbmp.c \
                $(cjpeg_SRC)/rdswitch.c $(cjpeg_SRC)/cdjpeg.c

djpeg_SRC = samples/djpeg
djpeg_SOURCES = $(djpeg_SRC)/djpeg.c $(djpeg_SRC)/wrppm.c $(djpeg_SRC)/wrgif.c $(djpeg_SRC)/wrtarga.c $(djpeg_SRC)/wrrle.c $(djpeg_SRC)/wrbmp.c \
                $(djpeg_SRC)/rdcolmap.c $(djpeg_SRC)/cdjpeg.c

jpegtran_SRC = samples/jpegtran
jpegtran_SOURCES = $(jpegtran_SRC)/jpegtran.c $(jpegtran_SRC)/rdswitch.c $(jpegtran_SRC)/cdjpeg.c $(jpegtran_SRC)/transupp.c

rdjpgcom_SOURCES = src/rdjpgcom.c
wrjpgcom_SOURCES = src/wrjpgcom.c


samples: $(samples_OUT)/cjpeg $(samples_OUT)/djpeg $(samples_OUT)/jpegtran $(samples_OUT)/rdjpgcom $(samples_OUT)/wrjpgcom

$(samples_OUT):
	mkdir -p $@	

$(samples_OUT)/cjpeg: $(cjpeg_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
	g++ -static $(cjpeg_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)
	
$(samples_OUT)/djpeg: $(djpeg_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
	g++ -static $(djpeg_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

$(samples_OUT)/jpegtran: $(jpegtran_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
	g++ -static $(jpegtran_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

$(samples_OUT)/rdjpgcom: $(rdjpgcom_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
	g++ -static $(rdjpgcom_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)

$(samples_OUT)/wrjpgcom: $(wrjpgcom_SOURCES) $(libjpeg_OUT)/libjpeg8c.a $(samples_OUT)
	g++ -static $(wrjpgcom_SOURCES) -o $@ $(CPPFLAGS) -L$(libjpeg_OUT) -ljpeg8c $(LIBS)
	