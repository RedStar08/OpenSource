module PC(
  input wire clk, reset,
  output wire [7:0] pc
);

  reg [7:0] pc_init;
  initial pc_init = 8'b00000000;
  assign  pc = pc_init;
  always@(posedge clk) begin  //clk???,pc???1
  
    if(reset == 1)
       pc_init = 0;
    else
       pc_init = pc_init + 1;
    
  end
  
endmodule