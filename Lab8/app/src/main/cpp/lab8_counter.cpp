#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("Lab8");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("Lab8")
//      }
//    }

#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

class Counter {
private:
    int counter;
public:
    Counter(): counter(0){};

    Counter(int local_counter): counter(local_counter) {};

    int getCounter() {
        return counter;
    }

    void increaseCounter() {
        counter++;
    }

    void resetCounter() {
        counter = 0;
    }
};

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_MainActivity_createCounter(JNIEnv *env, jclass clazz) {
    return (jlong) (new Counter());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_lab8_MainActivity_getCounter(JNIEnv *env, jclass clazz, jlong nativePointer) {
    return ((Counter *) nativePointer)->getCounter();
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_MainActivity_increaseCounter(JNIEnv *env, jclass clazz, jlong nativePointer) {
    Counter *pointer = ((Counter *) nativePointer);
    pointer->increaseCounter();
    return reinterpret_cast<jlong>(pointer);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_MainActivity_resetCounter(JNIEnv *env, jclass clazz, jlong nativePointer) {
    Counter *pointer = ((Counter *) nativePointer);
    pointer->resetCounter();
    return reinterpret_cast<jlong>(pointer);
}
