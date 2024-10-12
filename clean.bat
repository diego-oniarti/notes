@echo off
:: Batch script to clean LaTeX auxiliary files in all subdirectories

for /D %%d in (*) do (
    echo Cleaning LaTeX files in %%d
    cd %%d
    latexmk -c
    cd ..
)
echo Done!

