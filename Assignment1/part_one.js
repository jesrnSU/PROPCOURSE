/*
    Jesper Nilsson jeni3202
*/

const myObject = {
    
    call: function(funcName, parameters){
        function _dfs(current){
            if(current[funcName] instanceof Function){
                return current[funcName](parameters);
            }else{
                for(let i = 0; i < current.__proto__?.length; i++){
                    var result = _dfs(current.__proto__[i]);
                    if(result)
                        return result;
                }
            }
        };
        var foundFunction = _dfs(this);
        if(foundFunction)
            return foundFunction;
        throw new Error("No function found");
    },

    create: function(propotypeList) {
        function _circularInheritanceChecker(self, currentPrototype){
            if(self === currentPrototype){
                throw new Error("CIRCULAR ERROR");
            }else{
                for(let i = 0; i < currentPrototype.__proto__?.length; i++){
                    _circularInheritanceChecker(self, currentPrototype.__proto__[i]);
                }
            }
        };
        
        return { 
            addPrototype(prototype){
                _circularInheritanceChecker(this, prototype);

                if(this.__proto__ == null){
                    this.__proto__ = [prototype];
                }else{
                    this.__proto__.push(prototype);
                }
            }, 
            __proto__: (propotypeList == null) ? [] : propotypeList,
            create: myObject.create,
            call: myObject.call,
        }
    }
}