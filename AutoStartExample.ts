import AutoStart from "FuseJS/AutoStart"
import Lifecycle from "FuseJS/Lifecycle"

export default class AutoStartExample {
    hasPermission: boolean

    constructor() {
        this.refresh()

        // Refresh variables when returning from Android settings
        Lifecycle.on("enteringInteractive", () => this.refresh())
    }

    openSettings() {
        AutoStart.askForSystemAlertWindowPermission()
    }

    refresh() {
        this.hasPermission = AutoStart.hasSystemAlertWindowPermission()
        console.log("hasSystemAlertWindowPermission", this.hasPermission)
    }
}
