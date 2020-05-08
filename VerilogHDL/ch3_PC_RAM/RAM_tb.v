module RAM_tb;
  
  reg clk, WE;
  reg [15:0] in;
  reg [8:0] addr;
  wire [15:0] out;
   
  initial clk = 1'b0;
  always #5 clk = ~clk;
     
  initial begin
    //Write
    #0  WE = 0;
    #0 addr = 9'd0;
    in = 16'd10;
    #10 addr = 9'd1;
    in = 16'd20;
    #10 addr = 9'd2;
    in = 16'd30;
    #10 addr = 9'd3;
    in = 16'd40;
    //Read
    #10 WE = 1;
    #10 addr = 9'd0;
    #10 addr = 9'd1;
    #10 addr = 9'd2;
    #10 addr = 9'd3;
    
    #10 $stop;
    
  end
  
  RAM test(
    .clk(clk),
    .WE(WE),
    .in(in),
    .addr(addr),
    .out(out)
  );
  
endmodule

