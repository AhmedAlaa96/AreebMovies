#include <jni.h>

#include <string>

extern "C" JNIEXPORT jstring JNICALL Java_com_ahmed_areebmovies_retrofit_HeaderInterceptor_getAuthorizationValue(JNIEnv* env,jobject) {
  std::string tokenValue = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZTY5YzZhYzA5MDM0YTI3N2NkZGIyYjhhNmYyNjFhNyIsInN1YiI6IjY0ZTYyZjRjOTBlYTRiMDBjNzM4ODVmNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wfAILB4WSIrXDectu-MgnSXHf9AZPiea4HE_9TNPTMg";
  return env->NewStringUTF(tokenValue.c_str());
}