#include <string.h>
#include "toolkit.h"
#include "jniadapter.h"

/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enEpanet
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enEpanet
  (JNIEnv *env, jobject obj, jstring file1, jstring file2, jstring file3){
      jboolean iscopy;
	  const char *f1t = (*env)->GetStringUTFChars(env, file1, &iscopy);
	  const char *f2t = (*env)->GetStringUTFChars(env, file2, &iscopy);
	  const char *f3t = (*env)->GetStringUTFChars(env, file3, &iscopy);
	  if(f1t == NULL || f2t == NULL || f3t == NULL){
		  return 0;
	  }
	  //converter const char* para char*
	  char *f1 = strdup(f1t);
	  char *f2 = strdup(f2t);
	  char *f3 = strdup(f3t);
	  (*env)->ReleaseStringUTFChars(env, file1, f1t);
  	  (*env)->ReleaseStringUTFChars(env, file2, f2t);
  	  (*env)->ReleaseStringUTFChars(env, file3, f3t);
	  return ENepanet(f1, f2, f3, NULL);
}

/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enOpen
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enOpen
  (JNIEnv *env, jobject obj, jstring file1, jstring file2, jstring file3){
      jboolean iscopy;
	  const char *f1t = (*env)->GetStringUTFChars(env, file1, &iscopy);
	  const char *f2t = (*env)->GetStringUTFChars(env, file2, &iscopy);
	  const char *f3t = (*env)->GetStringUTFChars(env, file3, &iscopy);
	  if(f1t == NULL || f2t == NULL || f3t == NULL){
		  return 0;
	  }
	  //converter const char* para char*
	  char *f1 = strdup(f1t);
	  char *f2 = strdup(f2t);
	  char *f3 = strdup(f3t);
	  return ENopen(f1, f2, f3);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enClose
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enClose
  (JNIEnv *env, jobject obj){
	return ENclose();  	
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetNodeIndex
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetNodeIndex
  (JNIEnv *env, jobject obj, jstring idstring, jint index ){
      jboolean iscopy;
  	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
  	int index_int = index;
  	int *index_ptr = &index_int;
	return ENgetnodeindex( strdup(id), index_ptr );  	
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetNodeId
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetNodeId
  (JNIEnv *env, jobject obj, jint index , jstring idstring){
      jboolean iscopy;
	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
	return ENgetnodeid( index, strdup(id) );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetNodeType
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetNodeType
  (JNIEnv *env, jobject obj, jint index, jint typecode){
  	int typecode_int = typecode;
  	int *typecode_ptr = &typecode_int;
  	return ENgetnodetype( index, typecode_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetNodeValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetNodeValue
  (JNIEnv *env, jobject obj, jint index, jint paramcode, jfloat valuep){
	float value_float = valuep;
	float *value_ptr = &value_float;
	return ENgetnodevalue(index, paramcode, value_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetLinkIndex
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetLinkIndex
  (JNIEnv *env, jobject obj, jstring idstring, jint indexp){
      jboolean iscopy;
	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
  	int index_int = indexp;
  	int *index_ptr = &index_int;
	return ENgetlinkindex( strdup(id), index_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetLinkID
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetLinkID
  (JNIEnv *env, jobject obj, jint index, jstring idstring){
      jboolean iscopy;
	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
  	return  ENgetlinkid(index, strdup(id) );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetLinkType
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetLinkType
  (JNIEnv *env, jobject obj, jint index, jint typecodep){
  	int typecode_int = typecodep;
  	int *typecode_ptr = &typecode_int;
  	return ENgetlinktype(index, typecode_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetLinkNodes
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetLinkNodes
  (JNIEnv *env, jobject obj, jint index, jint fromnodep, jint tonodep){
	int fromnode_int = fromnodep;
  	int *fromnode_ptr = &fromnode_int;
	int tonode_int = tonodep;
  	int *tonode_ptr = &tonode_int;
  	return ENgetlinknodes( index, fromnode_ptr, tonode_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetLinkValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetLinkValue
  (JNIEnv *env, jobject obj, jint index, jint paramcode, jfloat valuep){
  	float value_float = valuep;
	float *value_ptr = &value_float;
  	return ENgetlinkvalue(index, paramcode, value_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetPatternID
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetPatternID
  (JNIEnv *env, jobject obj, jint index, jstring idstring){
      jboolean iscopy;
  	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
  	return ENgetpatternid(index, strdup(id));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetPatternIndex
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetPatternIndex
  (JNIEnv *env, jobject obj, jstring idstring, jint indexp){
      jboolean iscopy;
 	const char *id = (*env)-> GetStringUTFChars(env, idstring, &iscopy);
 	int index_int = indexp;
  	int *index_ptr = &index_int;
 	return ENgetpatternindex( strdup(id), index_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetPatternLen
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetPatternLen
  (JNIEnv *env, jobject obj, jint index, jint lenp){
 	int len_int = lenp;
  	int *len_ptr = &len_int;
  	return ENgetpatternlen(index, len_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetPatternValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetPatternValue
  (JNIEnv *env, jobject obj, jint index, jint period, jfloat valuep){
  	float value_float = valuep;
	float *value_ptr = &value_float;
  	return ENgetpatternvalue(index, period, value_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetControl
 * Signature: (IIIFIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetControl
  (JNIEnv *env, jobject obj, jint cindex, jint ctypep, jint lindexp, 
  	jfloat settingp, jint nindexp, jfloat levelp){
  	
  	int ctype_int = ctypep;
  	int *ctype_ptr = &ctype_int;
  	int lindex_int = lindexp;
  	int *lindex_ptr = &lindex_int;
  	float setting_float = settingp;
  	float *setting_ptr = &setting_float;
  	int nindex_int = nindexp;
  	int *nindex_ptr = &nindex_int;
  	float level_float = levelp;
  	float *level_ptr = &level_float;
  	
  	return  ENgetcontrol(cindex, ctype_ptr, lindex_ptr, setting_ptr, 
  							nindex_ptr, level_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetCount
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetCount
  (JNIEnv *env, jobject obj, jint countcode, jint countp){
  	int count_int = countp;
  	int *count_ptr = &count_int;
  	return ENgetcount(countcode, count_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetFlowUnits
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetFlowUnits
  (JNIEnv *env, jobject obj, jint unitscodep ){
  	int unitscode_int = unitscodep;
  	int *unitscode_ptr = &unitscode_int;
  	return ENgetflowunits(unitscode_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetTimeParam
 * Signature: (IJ)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetTimeParam
  (JNIEnv *env, jobject obj, jint paramcode, jlong timevaluep){
  	long timevalue_long = timevaluep;
  	long *timevalue_ptr = &timevalue_long;
  	return ENgettimeparam(paramcode, timevalue_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetQualType
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetQualType
  (JNIEnv *env, jobject obj, jint qualcodep, jint tracenodep){
  	int qualcode_int = qualcodep;
  	int *qualcode_ptr = &qualcode_int;
  	int tracenode_int = tracenodep;
  	int *tracenode_ptr = &tracenode_int;
  	return ENgetqualtype( qualcode_ptr, tracenode_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetOption
 * Signature: (IF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetOption
  (JNIEnv *env, jobject obj, jint optioncode, jfloat valuep){
  	float value_float = valuep;
  	float *value_ptr = &value_float;
  	return ENgetoption( optioncode, value_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetVersion
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetVersion
  (JNIEnv *env, jobject obj, jint vp){
  	int v_int = vp;
  	int *v_ptr = &v_int;
  	return ENgetversion(v_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetControl
 * Signature: (IIIFIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetControl
  (JNIEnv *env, jobject obj, jint cindex, jint ctype, jint lindex, jfloat setting, jint nindex, jfloat level){
  	return ENsetcontrol(cindex, ctype, lindex, setting,nindex,level );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetNodeValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetNodeValue
  (JNIEnv *env, jobject obj, jint index, jint paramcode, jfloat value){
  	return ENsetnodevalue( index, paramcode, value );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetLinkValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetLinkValue
  (JNIEnv *env, jobject obj, jint index, jint paramcode, jfloat value){
  	return ENsetlinkvalue( index, paramcode, value );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetPattern
 * Signature: (IFI)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetPattern
  (JNIEnv *env, jobject obj, jint index, jfloat factorsp, jint nfactors){
  	float factors_float = factorsp;
  	float *factors_ptr = &factors_float;
  	return  ENsetpattern(index, factors_ptr, nfactors );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetPatternValue
 * Signature: (IIF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetPatternValue
  (JNIEnv *env, jobject obj, jint index, jint period, jfloat value){
  	return ENsetpatternvalue( index, period, value );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetQualType
 * Signature: (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetQualType
  (JNIEnv *env, jobject obj, jint qualcode, jstring chemnamep, jstring chemunitsp, jstring tracenodep){
      jboolean iscopy;
  	const char *chemname = (*env)-> GetStringUTFChars(env, chemnamep, &iscopy);
  	const char *chemunits = (*env)-> GetStringUTFChars(env, chemunitsp, &iscopy);
  	const char *tracenode = (*env)-> GetStringUTFChars(env, tracenodep, &iscopy);
  	return ENsetqualtype(qualcode, strdup(chemname), strdup(chemunits), strdup(tracenode));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetTimeParam
 * Signature: (IJ)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetTimeParam
  (JNIEnv *env, jobject obj, jint paramcode, jlong timevalue){
  	return ENsettimeparam( paramcode, timevalue );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetOption
 * Signature: (IF)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetOption
  (JNIEnv *env, jobject obj, jint optioncode, jfloat value){
  	return ENsetoption(optioncode, value);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSaveHydFile
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSaveHydFile
  (JNIEnv *env, jobject obj, jstring fnamep){
      jboolean iscopy;
  	const char *fname = (*env)-> GetStringUTFChars(env, fnamep, &iscopy);
  	return ENsavehydfile( strdup(fname));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enUseHydFile
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enUseHydFile
  (JNIEnv *env, jobject obj, jstring fnamep){
      jboolean iscopy;
  	const char *fname = (*env)-> GetStringUTFChars(env, fnamep, &iscopy);
  	return ENusehydfile(strdup(fname));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSolveH
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSolveH
  (JNIEnv *env, jobject obj){
  	return ENsolveH();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enOpenH
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enOpenH
  (JNIEnv *env, jobject obj){
  	return ENopenH();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enInitH
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enInitH
  (JNIEnv *env, jobject obj, jint flag){
  	return ENinitH( flag );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enRunH
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enRunH
  (JNIEnv *env, jobject obj, jlong tp){
  	long t_long = tp;
  	long *t_ptr = &t_long;
  	return ENrunH(t_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enNextH
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enNextH
  (JNIEnv *env, jobject obj, jlong tstepp){
  	long tstep_long = tstepp;
  	long *tstep_ptr = &tstep_long;
  	return ENnextH(tstep_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enCloseH
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enCloseH
  (JNIEnv *env, jobject obj){
  	return ENcloseH();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSolveQ
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSolveQ
  (JNIEnv *env, jobject obj){
  	return ENsolveQ();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enOpenQ
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enOpenQ
  (JNIEnv *env, jobject obj){
  	return ENopenQ();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enInitQ
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enInitQ
  (JNIEnv *env, jobject obj, jint saveflag ){
  	return ENinitQ(saveflag);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enRunQ
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enRunQ
  (JNIEnv *env, jobject obj, jlong tp){
  	long t_long = tp;
  	long *t_ptr = &t_long;
  	return ENrunQ(t_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enNextQ
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enNextQ
  (JNIEnv *env, jobject obj, jlong tstepp){
  	long tstep_long = tstepp;
  	long *tstep_ptr = &tstep_long;
  	return ENnextQ(tstep_ptr );
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enStepQ
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enStepQ
  (JNIEnv *env, jobject obj, jlong tleftp){
  	long tleft_long = tleftp;
  	long *tleft_ptr = &tleft_long;
  	return ENstepQ(tleft_ptr);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enCloseQ
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enCloseQ
  (JNIEnv *env, jobject obj){
  	return ENcloseQ();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSaveH
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSaveH
  (JNIEnv *env, jobject obj){
  	return ENsaveH();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSaveInpFile
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSaveInpFile
  (JNIEnv *env, jobject obj, jstring fnamep){
      jboolean iscopy;
  	const char *fname = (*env)-> GetStringUTFChars(env, fnamep, &iscopy);
  	return  ENsaveinpfile(strdup(fname));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enReport
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enReport
  (JNIEnv *env, jobject obj){
  	return ENreport();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enResetReport
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enResetReport
  (JNIEnv *env, jobject obj){
  	return ENresetreport();
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetReport
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetReport
  (JNIEnv *env, jobject obj, jstring commandp){
      jboolean iscopy;
  	const char *command = (*env)-> GetStringUTFChars(env, commandp, &iscopy);
  	return ENsetreport(strdup(command));
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enSetStatusReport
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enSetStatusReport
  (JNIEnv *env, jobject obj, jint statuslevel){
  	return ENsetstatusreport(statuslevel);
}
/*
 * Class:     JEpanetToolKitWrapper
 * Method:    enGetError
 * Signature: (ILjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_ourgrid_epanetgrid_ToolkitAdapter_enGetError
  (JNIEnv *env, jobject obj, jint errcode, jstring errmsgp, jint nchar){
      jboolean iscopy;
  	const char *errmsg = (*env)-> GetStringUTFChars(env, errmsgp, &iscopy);
  	return ENgeterror(errcode, strdup(errmsg), nchar);
}
