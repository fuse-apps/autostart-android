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

        AddMember(new NativeFunction("restartOnDestroy", (context, args) => {
            if defined(ANDROID)
            {
                if (args.Length != 0)
                {
                    try { EnableRestartOnDestroyJava((bool) args[0]); }
                    catch (Exception e) { debug_log e.ToString(); }
                }

                return IsRestartOnDestroyEnabledJava();
            }

            return null;
        }));
    }

    [Foreign(Language.Java)]
    extern(ANDROID) bool HasSystemAlertWindowPermissionJava()
    @{
        return com.fuse.immortal.BootCompletedReceiver.hasSystemAlertWindowPermission();
    @}

    [Foreign(Language.Java)]
    extern(ANDROID) void AskForSystemAlertWindowPermissionJava()
    @{
        com.fuse.immortal.BootCompletedReceiver.askForSystemAlertWindowPermission();
    @}

    [Foreign(Language.Java)]
    extern(ANDROID) bool IsRestartOnDestroyEnabledJava()
    @{
        return com.fuse.immortal.ImmortalActivity.isRestartOnDestroyEnabled();
    @}

    [Foreign(Language.Java)]
    extern(ANDROID) void EnableRestartOnDestroyJava(bool enabled)
    @{
        com.fuse.immortal.ImmortalActivity.enableRestartOnDestroy(enabled);
    @}
}
