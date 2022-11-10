/** Native AutoStart module - implemented by AutoStartModule.uno */
declare module "FuseJS/AutoStart" {
    function hasSystemAlertWindowPermission(): boolean

    function askForSystemAlertWindowPermission(): void

    function requestAutoStartPermissions(): void
}
