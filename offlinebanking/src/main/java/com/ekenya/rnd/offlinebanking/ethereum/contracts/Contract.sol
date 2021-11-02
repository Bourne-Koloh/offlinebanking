contract ContractExample {
    event e(bool);
    event multipleEvent(bool,bool,bool);

    void foo(){
        //[...]
    }

    uint bar(int a){
        return 0
    }

    event eventOutputMatrix(int[3][3]){
        //[...]
    }

    function functionOutputMatrix() returns(uint8[2][8]){
        //[...]
    }

    function functionInputMatrix(uint8[3]){
        //[...]
    }

    function functionOutputMultiple() returns(bool,uint8[2][3]){
        //[...]
    }
}