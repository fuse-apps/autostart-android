import AutoStart from "FuseJS/AutoStart"
import Lifecycle from "FuseJS/Lifecycle"

export default class AutoStartExample {
    hasPermission: boolean

    constructor() {
        this.refresh()

        // Refresh variables when returning from system settings
        Lifecycle.on("enteringInteractive", () => this.refresh())
    }

    openSettings() {
        AutoStart.askForPermission()
    }

    refresh() {
        this.hasPermission = AutoStart.hasPermission()
        console.log("hasPermission", this.hasPermission)

        // Enable restart-on-destroy as long as the permission is granted
        AutoStart.restartOnDestroy(this.hasPermission)
    }
}
