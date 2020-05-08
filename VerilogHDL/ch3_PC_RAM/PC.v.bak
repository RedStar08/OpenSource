//PC
module PC(
  input wire clk, reset,
  output reg [7:0] pc
);

  always@(posedge clk) begin
  
    if(reset == 1)
       pc = 8'd0;
    else
       pc = pc + 1;
    
  end
  
endmodule

