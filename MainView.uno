using Fuse.Android.Permissions;
using Uno.Compiler.ExportTargetInterop;
using Uno;

partial class MainView
{
    public MainView()
    {
        InitializeUX();

        if defined(ANDROID)
            IntializeAutoStart();
    }

    extern(ANDROID) bool IntializeAutoStart()
    {
        if (!HasSystemAlertWindowPermissionJava())
        {
            AskForSystemAlertWindowPermissionJava();
            return false;
        }

        RequestAutoStartPermissions();
        return true;
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
