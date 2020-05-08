//Register_File
module Register(
  input wire WE, clk,
  input wire [2:0] R_port0, R_port1, W_port,
  input wire [15:0] Write,
  output reg [15:0] Read0, Read1
);
  
  reg [15:0] regfile[0:7];
  
  always@* begin    
    Read0 = regfile[R_port0];
    Read1 = regfile[R_port1];
  end
  
  always@(posedge clk) begin
      if(WE == 1)
        regfile[W_port] <= Write;
  end
  
endmodule