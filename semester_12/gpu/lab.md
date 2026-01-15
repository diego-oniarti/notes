# COO
Store a sparse matrix by saving 3 vectors
- x coordinates of nonzero elements
- y coordinates of nonzero elements
- values of nonzero elements

# CSR
Store a sparse matrix by saving 3 vectors
- the values of nonzero elements
- x coordinates of nonzero elements
- y coordinates of nonzero elements (Stored in prefix-sum)
    - or the index pointer to the first element in a row
