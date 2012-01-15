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
LIBS = $(TARGET_LIB)/bitmaps.dll.a
CUSTOM_INCLUDES = -I"../bitmaps/include"
SOURCE = src
TARGET = ../target
JAVA_JNI_CLASSES = com.cateye.core.native_.RawImageLoader \
                   com.cateye.core.native_.RawImageDescription
NATIVE_LIB = Raw.CatEyeImageLoader

include ../target/makefiles/cpp_java_cross.mk