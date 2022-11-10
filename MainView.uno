using Fuse.Android.Permissions;
using Uno.Compiler.ExportTargetInterop;
using Uno;

partial class MainView
{
    public MainView()
    {
        InitializeUX();

        if defined(ANDROID)
        {
            if (InitBootCompletedReceiver())
                RequestPermissions();
        }
    }

    [Foreign(Language.Java)]
    extern(ANDROID) bool InitBootCompletedReceiver()
    @{
        return com.fuse.BootCompletedReceiver.init();
    @}

    extern(ANDROID) void RequestPermissions()
    {
        debug_log "Requesting permissions";
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
