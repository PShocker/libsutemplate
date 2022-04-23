#include <jni.h>
#include <string>
#include <unistd.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_shocker_libsutemplate_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_shocker_libsutemplate_AIDLService_nativeGetUid(JNIEnv *env, jobject thiz) {
    // TODO: implement nativeGetUid()
    return getuid();
}