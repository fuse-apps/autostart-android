using Fuse;
using Fuse.Scripting;
using Uno;
using Uno.UX;
using Uno.Compiler.ExportTargetInterop;

[UXGlobalModule]
class AutoStartModule : NativeModule
{
    static bool _inited;

    public AutoStartModule()
    {
        if (_inited) 
            return;

        _inited = true;
        Resource.SetGlobalKey(this, "FuseJS/AutoStart");

        AddMember(new NativeFunction("hasPermission", (context, args) => {
            if defined(ANDROID)
                return HasSystemAlertWindowPermissionJava();

            return false;
        }));

        AddMember(new NativeFunction("askForPermission", (context, args) => {
            if defined(ANDROID)
                AskForSystemAlertWindowPermissionJava();
            
            return null;
        }));
    }

    [Foreign(Language.Java)]
    extern(ANDROID) bool HasSystemAlertWindowPermissionJava()
    @{
        return com.fuse.BootCompletedReceiver.hasSystemAlertWindowPermission();
    @}

    [Foreign(Language.Java)]
    extern(ANDROID) void AskForSystemAlertWindowPermissionJava()
    @{
        com.fuse.BootCompletedReceiver.askForSystemAlertWindowPermission();
    @}
}
