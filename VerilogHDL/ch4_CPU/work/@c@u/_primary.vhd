library verilog;
use verilog.vl_types.all;
entity CU is
    port(
        Ins_op          : in     vl_logic_vector(3 downto 0);
        wr_en           : out    vl_logic;
        m_wr_en         : out    vl_logic;
        select0         : out    vl_logic;
        select1         : out    vl_logic;
        alu_op          : out    vl_logic_vector(2 downto 0)
    );
end CU;
