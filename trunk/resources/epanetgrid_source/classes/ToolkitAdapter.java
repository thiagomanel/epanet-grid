// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 18/6/2007 16:36:07
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ToolkitAdapter.java

package org.ourgrid.epanetgrid;


public class ToolkitAdapter
{

    public ToolkitAdapter()
    {
    }

    public native int enEpanet(String s, String s1, String s2);

    public native int enOpen(String s, String s1, String s2);

    public native int enClose();

    public native int enGetNodeIndex(String s, int i);

    public native int enGetNodeId(int i, String s);

    public native int enGetNodeType(int i, int j);

    public native int enGetNodeValue(int i, int j, float f);

    public native int enGetLinkIndex(String s, int i);

    public native int enGetLinkID(int i, String s);

    public native int enGetLinkType(int i, int j);

    public native int enGetLinkNodes(int i, int j, int k);

    public native int enGetLinkValue(int i, int j, float f);

    public native int enGetPatternID(int i, String s);

    public native int enGetPatternIndex(String s, int i);

    public native int enGetPatternLen(int i, int j);

    public native int enGetPatternValue(int i, int j, float f);

    public native int enGetControl(int i, int j, int k, float f, int l, float f1);

    public native int enGetCount(int i, int j);

    public native int enGetFlowUnits(int i);

    public native int enGetTimeParam(int i, long l);

    public native int enGetQualType(int i, int j);

    public native int enGetOption(int i, float f);

    public native int enGetVersion(int i);

    public native int enSetControl(int i, int j, int k, float f, int l, float f1);

    public native int enSetNodeValue(int i, int j, float f);

    public native int enSetLinkValue(int i, int j, float f);

    public native int enSetPattern(int i, float f, int j);

    public native int enSetPatternValue(int i, int j, float f);

    public native int enSetQualType(int i, String s, String s1, String s2);

    public native int enSetTimeParam(int i, long l);

    public native int enSetOption(int i, float f);

    public native int enSaveHydFile(String s);

    public native int enUseHydFile(String s);

    public native int enSolveH();

    public native int enOpenH();

    public native int enInitH(int i);

    public native int enRunH(long l);

    public native int enNextH(long l);

    public native int enCloseH();

    public native int enSolveQ();

    public native int enOpenQ();

    public native int enInitQ(int i);

    public native int enRunQ(long l);

    public native int enNextQ(long l);

    public native int enStepQ(long l);

    public native int enCloseQ();

    public native int enSaveH();

    public native int enSaveInpFile(String s);

    public native int enReport();

    public native int enResetReport();

    public native int enSetReport(String s);

    public native int enSetStatusReport(int i);

    public native int enGetError(int i, String s, int j);

    static 
    {
        System.loadLibrary("nativelib");
    }
}