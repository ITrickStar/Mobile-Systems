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


class StringList {
private:
    std::vector<std::string> list;
    std::string result;
    bool isAdded;

public:
    StringList() = default;

    bool push(const std::string &input) {
        if (!(std::find(list.begin(), list.end(), input) != list.end()))
        {
            list.emplace_back(input);

            std::string toUp;
            std::string toLow;
            if (list.size() == 1) {
                if (list[0].size() != 0) {
                    toUp = list[0].substr(0, 1);
                    for (auto &c: toUp) result += toupper(c);
                    toLow = list[0].substr(1, list[0].size() - 1);
                    for (auto &c: toLow) result += tolower(c);
                }
            } else {
                toLow = input.substr(0, input.size());
                result += ", ";
                for (auto &c: toLow) result += tolower(c);
            }
            isAdded = true;
            return true;
        }
        isAdded = false;
        return false;
    }

    void pop() {
        if (list.size() == 1) {
            result = "";
        } else {
            result = result.substr(0, result.size() - list[list.size() - 1].size() - 2);
        }
        list.pop_back();
    }

    const char *get(int position) {
        return list[position].c_str();
    }

    const char *getAll() {
        return result.c_str();
    }

    const bool getIsAdded() {
        return isAdded;
    }
};


extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_StringListActivity_createStringList(JNIEnv *env,
                                                          jclass clazz) {
    return (jlong) (new StringList());
}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_StringListActivity_pushStringList(JNIEnv *env,
                                                        jclass clazz,
                                                        jstring element,
                                                        jlong nativePointer) {
    StringList *pointer = ((StringList *) nativePointer);
    pointer->push(env->GetStringUTFChars(element, nullptr));
    return reinterpret_cast<jlong> (pointer);
}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_lab8_StringListActivity_popStringList(JNIEnv *env, jclass clazz,
                                                       jlong nativePointer) {
    StringList *pointer = ((StringList *) nativePointer);
    pointer->pop();
    return reinterpret_cast<jlong>(pointer);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_lab8_StringListActivity_getStringList(JNIEnv *env, jclass clazz,
                                                       jint position,
                                                       jlong nativePointer) {
    return env->NewStringUTF((((StringList *) nativePointer)->get(position)));
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_lab8_StringListActivity_getAllStringList(JNIEnv *env,
                                                          jclass clazz,
                                                          jlong nativePointer) {
    return env->NewStringUTF((((StringList *) nativePointer)->getAll()));
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_lab8_StringListActivity_getIsAdded(JNIEnv *env, jclass clazz,
                                                    jlong nativePointer) {
    return ((StringList *) nativePointer)->getIsAdded();
}
