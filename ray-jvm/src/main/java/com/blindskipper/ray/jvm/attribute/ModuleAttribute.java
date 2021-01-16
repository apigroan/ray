package com.blindskipper.ray.jvm.attribute;


import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.benua.AccessFlagType;
import com.blindskipper.ray.jvm.constant.ConstantPool;
import com.blindskipper.ray.jvm.data.type.U2CpIndex;

public class ModuleAttribute extends AttributeInfo {

    {
        u2cp ("module_name_index");
        u2   ("module_flags");
        u2cp ("module_version_index");
        u2   ("requires_count");
        table("requires", Require.class);
        u2   ("exports_count");
        table("exports", Export.class);
        u2   ("opens_count");
        table("opens", Open.class);
        u2   ("uses_count");
        table("uses_index", U2CpIndex.class);
        u2   ("provides_count");
        table("provides", Provide.class);
    }

    public static class Require extends ClassAssembly {

        {
            u2cp("requires_index");
            u2af("requires_flags", AccessFlagType.AF_MODULE_ATTR);
            u2cp("requires_version_index");
        }

        @Override
        protected void postRead(ConstantPool cp) {
            String moduleName = cp.getConstantDesc(super.getUInt("requires_index"));
            int idx = super.getUInt("requires_version_index");
            if (idx == 0) {
                setDescription(moduleName);
            } else {
                String version = cp.getConstantDesc(idx);
                setDescription(moduleName + "@" + version);
            }
        }

    }

    public static class Export extends ClassAssembly {

        {
            u2cp ("exports_index");
            u2af ("exports_flags", AccessFlagType.AF_MODULE_ATTR);
            u2   ("exports_to_count");
            table("exports_to", U2CpIndex.class);
        }

        @Override
        protected void postRead(ConstantPool cp) {
            setDescription(cp.getConstantDesc(super.getUInt("exports_index")));
        }

    }

    public static class Open extends ClassAssembly {

        {
            u2cp ("opens_index");
            u2af ("opens_flags", AccessFlagType.AF_MODULE_ATTR);
            u2   ("opens_to_count");
            table("opens_to_index", U2CpIndex.class);
        }

        @Override
        protected void postRead(ConstantPool cp) {
            setDescription(cp.getConstantDesc(super.getUInt("opens_index")));
        }

    }

    public static class Provide extends ClassAssembly {

        {
            u2cp ("provides_index");
            u2   ("provides_with_count");
            table("provides_with_index", U2CpIndex.class);
        }

        @Override
        protected void postRead(ConstantPool cp) {
            setDescription(cp.getConstantDesc(super.getUInt("provides_index")));
        }

    }

}
