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
JAVA_JNI_CLASSES = com.cateye.core.jni.PreciseBitmap \
                   com.cateye.core.jni.PreviewBitmap
NATIVE_LIB = CatEyeB.Core.Native

include ../target/makefiles/cpp_java_cross.mk