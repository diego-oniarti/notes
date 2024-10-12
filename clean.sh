#!/bin/bash

# Loop through all subdirectories in the current directory
for dir in */ ; do
    if [ -d "$dir" ]; then
        echo "Executing latexmk -c in $dir"
        (cd "$dir" && latexmk -c)
    fi
done

echo "Done!"

