#################### Macros #####################

PROJ = CatEyeB.Core

# CC macros
COMMON_CFLAGS = -static-libgcc
CFLAGS = -O3
CDEFINES =

# CPP macros (additional to C macros)
COMMON_CPPFLAGS = -static-libstdc++
CPPFLAGS = 
CPPDEFINES =

# Universal macros
LIBS = $(TARGET_LIB)/bitmaps.dll.a $(TARGET_LIB)/jpeg8c.dll.a $(TARGET_LIB)/raw.a -lws2_32
	
CUSTOM_INCLUDES = -I"../bitmaps/include" -I"../libraw/libraw" -I"../jpeg-8c/include"
SOURCE = src
TARGET = ../target
JAVA_JNI_CLASSES = com.cateye.core.jni.RawImageLoader
NATIVE_LIB = Raw.CatEyeImageLoader

include ../target/makefiles/cpp_java_cross.mk