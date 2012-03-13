#################### Macros #####################

PROJ = CatEyeB.BasicStageOperations

# CC macros
COMMON_CFLAGS = -static-libgcc
CFLAGS = -O0 -g2
CDEFINES =

# CPP macros (additional to C macros)
COMMON_CPPFLAGS = -static-libstdc++
CPPFLAGS = 
CPPDEFINES =

# Universal macros
LIBS = $(TARGET_LIB)/bitmaps.dll.a $(TARGET_LIB)/colorlib.a 
CUSTOM_INCLUDES = -I"../bitmaps/include" -I"../colorlib/include"
SOURCE = src
TARGET = ../target
JAVA_JNI_CLASSES = com.cateye.stageoperations.hsb.HSBStageOperationProcessor				\
                   com.cateye.stageoperations.rgb.RGBStageOperationProcessor				\
                   com.cateye.stageoperations.limiter.LimiterStageOperationProcessor		\
                   com.cateye.stageoperations.compressor.CompressorStageOperationProcessor	\
                   com.cateye.stageoperations.downsample.DownsampleStageOperationProcessor
NATIVE_LIB = Basic.CatEyeStageOperation

include ../target/makefiles/cpp_java_cross.mk