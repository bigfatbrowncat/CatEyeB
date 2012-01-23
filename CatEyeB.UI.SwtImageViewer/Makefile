#################### Macros #####################

PROJ = CatEyeB.UI.SwtImageViewer

# CC macros
COMMON_CFLAGS = -static-libgcc
CFLAGS = -O3
CDEFINES =

# CPP macros (additional to C macros)
COMMON_CPPFLAGS = -static-libstdc++
CPPFLAGS = 
CPPDEFINES =

# Universal macros
LIBS = $(TARGET_LIB)/bitmaps.dll.a -lgdi32
CUSTOM_INCLUDES = -I"../bitmaps/include"
SOURCE = src
TARGET = ../target
JAVA_JNI_CLASSES = com.cateye.ui.swt.ImageViewer \
                   com.cateye.ui.swt.PreviewImageViewer \
                   com.cateye.ui.swt.PreciseImageViewer
NATIVE_LIB = ImageViewer.SwtWidgets

include ../target/makefiles/cpp_java_cross.mk