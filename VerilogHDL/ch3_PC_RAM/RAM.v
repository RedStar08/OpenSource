//RAM (1KB = 512WB)
module RAM(
  input wire clk, WE,
  input wire [15:0] in,
  input wire [8:0] addr,
  output reg [15:0] out
);
  
  reg [15:0] regfile[511:0];
  
  always@* begin
    if(WE == 1)
      out = regfile[addr];
  end
  
  always@(posedge clk) begin
    if(WE == 0)
      regfile[addr] <= in;
  end
  
endmodule
