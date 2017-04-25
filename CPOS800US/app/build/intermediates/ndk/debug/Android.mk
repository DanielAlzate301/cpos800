LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := app
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni\Android.mk \
	D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni\any.c \
	D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni\DES\DES.cpp \
	D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni\ICCARD\Android.mk \
	D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni\rfid15693\Android.mk \

LOCAL_C_INCLUDES += D:\Apps\Android\AndroidStudio\CPOS800US\app\src\main\jni
LOCAL_C_INCLUDES += D:\Apps\Android\AndroidStudio\CPOS800US\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
