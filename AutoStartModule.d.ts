/** Native AutoStart module - implemented by AutoStartModule.uno */
declare module "FuseJS/AutoStart" {

    /** Returns whether required permissions are granted */
    function hasPermission(): boolean

    /** Opens system settings to edit permissions */
    function askForPermission(): void

    /** Restarts app again automatically when destroyed */
    function restartOnDestroy(enabled?: boolean): boolean
}
