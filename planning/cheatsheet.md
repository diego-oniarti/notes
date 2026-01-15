# Start the docker
`palopoli`

# Attatch to the docker
`docker exec -it docker_container /bin/bash`

# rqt_console
```sh
rosrun rqt_console rqt_console
rosrun rqt_logger_level rqt_logger_level
```

# PlotJuggler
```sh
rosrun plotjuggler plotjuggler
```

# Making a Catkin package
```sh
cd ~/ros_ws/src/
catkin_create_pkg [name] [dep1] [dep2] [dep3...]
cd ..
catkin_make
source devel/setup.bash
```

# Launch
```sh
roslaunch [package] [filename.launch]
```
