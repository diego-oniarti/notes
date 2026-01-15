ROS Distro: Jazzy

```
source /opt/ros/jazzy/setup.zsh
source install/setup.zsh
```

Node management
```
ros2 run  <package_name> <node_name>
ros2 node list
ros2 node info <node_name>
```

Messages
```
ros2 topic type /topic
ros2 topic pub  /topic type data
```

Running the console
```
ros2 run rqt_console rqt_console
```

Building the workspace
```
coldon build --symlink-install
```

Packages
```
ros2 pkg create <name> --build-type ament_cmake
ros2 pkg create <name> --build-type ament_cmake --node-name <node_name>
```

# Workflow
1. Create a package
2. Create a node in the `src` directory
3. Add dependencies to the `package.xml`
Example
```
<depend>std_msgs</depend>
<depend>rclcpp</depend>
```

4. Add dependencies to CMakeLists.txt
Example
    ```
    find_package(rclcpp REQUIRED)
    find_package(std_msgs REQUIRED)
    add_executable(mytalker src/publisher_node.cpp)
ament_target_dependencies(mytalker rclcpp std_msgs)
    install(TARGETS
            mytalker
            DESTINATION lib/${PROJECT_NAME})
    ```

    5. Check for dependencies
    Run this in `ros2_ws`
    ```
    sudo apt install python3-rosdep
    rosdep update
    rosdep install -i --from-path src --rosdistro jazzy -y
    ```

    Run this in the project
    ```
    colcon build
    source install/setup.zsh
    ```

    6. Run it
    ```
    ros2 run <pkg_name> <name>
    ```

# Gazebo
Instead of `ign gazebo`, Jazzy uses `gz sim`

## Gazebo demo
```sh
gz sim -v 4 -r visualize_lidar.sdf

ros2 run ros_gz_bridge parameter_bridge /model/vehicle_blue/cmd_vel@geometry_msgs/msg/Twist]ignition.msgs.Twist

ros2 run teleop_twist_keyboard teleop_twist_keyboard --ros-args -r /cmd_vel:=/model/vehicle_blue/cmd_vel
```
