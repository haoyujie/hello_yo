DESCRIPTION = "Prints Hello World"
PN='hello_yo'
PV='1'

python do_build() {
    bb.debug(2, "Starting to figure out the task list");
    bb.plain("********************");
    bb.plain("Hello yo");
    bb.plain("********************");
    bb.plain("********************");
    bb.note("There are 47 tasks to run");
}

python do_clean() {
    bb.plain(" clean");
}

