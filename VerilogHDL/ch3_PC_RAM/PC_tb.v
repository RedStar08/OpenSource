module PC_tb;
  
  reg clk, reset;
  wire [7:0] pc;
   
  initial clk = 1'b0;
  always #5 clk = ~clk;
     
  initial begin
    #0 reset = 1;
    #10 reset = 0;
    #30 reset = 1;
    #10 reset = 0;
    #50 $stop;
    
  end
  
  PC test(
    .clk(clk),
    .reset(reset),
    .pc(pc)
  );
  
endmodule
