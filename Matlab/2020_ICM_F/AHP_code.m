
%% Input judgment matrix
A = input('Judgment matrix A=')


%% Method 1: Arithmetic average method for weighting
sum_A = sum(A);
[n,n] = size(A);  
SUM_A = repmat(sum_A,n,1);  
Stand_A = A ./ SUM_A;
disp('The result of calculating the weight by the arithmetic mean method is:');
disp(sum(Stand_A,2) / n);

%% Method 2: Geometric average method for weighting
Product_A = prod(A,2);
Product_nA = Product_A .^ (1/n);
disp('The result of calculating the weight by the Geometric mean method is:');
disp(Product_nA ./ sum(Product_nA));

%% Method 3: Eigenvalue  method for weighting
[V,D] = eig(A);    
max_d = max(max(D)); 
D == max_d;
[r,c] = find(D == max_d , 1);
V(:,c);		
disp('The result of calculating the weight by the Eigenvalue method is:');
disp( V(:,c) ./ sum(V(:,c)) );

%% Calculate the consistency ratio CR

CI = (max_d- n) / (n-1);
RI=[0 0 0.52 0.89 1.12 1.26 1.36 1.41 1.46 1.49 1.52 1.54 1.56 1.58 1.59];  
CR=CI/RI(n);
disp('Consistency index CI=');disp(CI);
disp('Consistency ratio CR=');disp(CR);
if CR<0.10
    disp('CR < 0.10,So the consistency of the judgment matrix A is acceptable!!');
else
    disp('Caution:CR >= 0.10 Therefore, the judgment matrix A needs to be modified!!');
end



