#################### Macros #####################

PROJ = CatEyeB.BasicStageOperations

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
JAVA_JNI_CLASSES = com.cateye.stageoperations.brightness.BrightnessStageOperationProcessor	\
                   com.cateye.stageoperations.limiter.LimiterStageOperationProcessor
NATIVE_LIB = Basic.CatEyeStageOperation

include ../target/makefiles/cpp_java_cross.mk