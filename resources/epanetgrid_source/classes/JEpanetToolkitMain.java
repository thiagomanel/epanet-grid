// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 18/6/2007 16:36:35
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   JEpanetToolkitMain.java

package org.ourgrid.epanetgrid;


// Referenced classes of package org.ourgrid.epanetgrid:
//            ToolkitAdapter

public class JEpanetToolkitMain
{

    public JEpanetToolkitMain()
    {
    }

    public static void main(String args[])
    {
        ToolkitAdapter toolkitAdapter = new ToolkitAdapter();
        toolkitAdapter.enEpanet(args[0], args[1], args[2]);
    }
}