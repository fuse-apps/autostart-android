using Fuse;
using Fuse.Scripting;
using Fuse.Android.Permissions;
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

        AddMember(new NativeFunction("hasSystemAlertWindowPermission", (context, args) => {
            if defined(ANDROID)
                return HasSystemAlertWindowPermissionJava();

            return false;
        }));

        AddMember(new NativeFunction("askForSystemAlertWindowPermission", (context, args) => {
            if defined(ANDROID)
                AskForSystemAlertWindowPermissionJava();
            
            return null;
        }));

        AddMember(new NativeFunction("requestAutoStartPermissions", (context, args) => {
            if defined(ANDROID)
                RequestAutoStartPermissions();
            
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

    extern(ANDROID) void RequestAutoStartPermissions()
    {
        Permissions
            .Request(new[] {
                Permissions.Android.RECEIVE_BOOT_COMPLETED,
                Permissions.Android.SYSTEM_ALERT_WINDOW
            })
            .Then(
                permissions => { debug_log "Permissions granted"; },
                error => { debug_log "Permissions error: " + error.Message; }
            );
    }
}
