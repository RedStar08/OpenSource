module compare_8(equal, a, b);
	input [7:0]a, b;
	output equal;
	reg equal;
	
	always @(a or b)
		if(a > b)
			equal = 1;
		else
			equal = 0;
endmodule