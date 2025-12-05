/*
    Jesper Nilsson jeni3202
    
    function createClass returns a classobject with it's own name and list containing superclasses.
    To minimize memory usage every classobject will share the new() and addSuperClass() function.
    Creating 10000 classes will only point to one new function, same goes for addSuperClass(). 

    Important: When iterating thru the inheritance tree I had to share the classes superclasslist in dfsFunction. Classes does not
    have access to the call function so I had to make the superclass list accessible from each class while making sure
    it's visibility does not allow access from the outside, otherwise there is nothing stopping someone from fetching the list and 
    modifying it (doesnt matter if you set writable, configurable to false. I tried that). I could just return a copy of the list, but that
    that would mean that everytime I iterate through the supers internally (etc dfsFunction) I'd have to create copies all the time. 
    
    so my final solution after a lot of iterations was to instead implement the callback pattern which is when you pass a function 
    to a function. This way I could make sure _supers only returns one Class at a time and does not have to copy at all but use the direct
    reference for performance. 

    Note: I tried to implement this with the module design pattern but was unsuccessful, is it possible? I felt like all my tried solutions
    had some kind of performance or accessibility issue and fixing that issue just created another one. 
    
    Note2: I could have moved the shared functions outside the createClass function for maximum performance. All classes would then share these.
    I was not sure if it was allowed since I thought you maybe only wanted one function "createClass".
*/
function createClass(className, superClassList){
    // Have to keep in mind that if an user sends in an array with superclasses, they can still access that array and modify it
    // slice() will copy the array to a new object while keeping the actual class objects. 
    const supers = (superClassList == null) ? [] : superClassList.slice(); 

    // Functions for each class. 
    function sharedCall(funcName, ...parameters){ 
        let foundFunc = dfsFunction(this._proto, funcName);

        if(foundFunc)
            return foundFunc(...parameters);
        throw new Error("No function found");
    }

    function dfsFunction(currentClass, funcName){
        if(typeof currentClass[funcName] === 'function'){
            return currentClass[funcName];
        }
        var foundFunc = null;

        currentClass._supers(function(superClass){
            foundFunc = dfsFunction(superClass, funcName)
            return foundFunc !== null;
        });
        return foundFunc;
    }

    // Shared functions for Class Objects 
    function sharedNew(){
        // Each actual object stores which class created it
        const classObj = this;
        return {
            _proto: classObj,
            call: sharedCall
        }
    };

    function sharedAddSuperClass(prototype){
        dfsCheckCircular(prototype, this); 
        supers.push(prototype);
    };

    function dfsCheckCircular(current, childClass){
        if(current === childClass){
            throw new Error("Circular inheritance");
        }else{
            current._supers(function(superClass){
                dfsCheckCircular(superClass, childClass);
            });
        }
    };

    // Return the following when running createClass
    const classObject = {
        name: className,
        new: sharedNew,
        addSuperClass: sharedAddSuperClass
    }

    // Solution to limit access to the superclass list while still being performant, using no copies with minimal logic/calculations. 
    Object.defineProperty(classObject, "_supers", {
        value(callback){
            let nrSupers = supers.length;
            for(let i = 0; i < nrSupers; i++){
                var stopLoop = callback(supers[i]);
                if(stopLoop) 
                    return;
            }
        }, 
        writable: false,
        configurable: false,
        enumerable: false,
    });
    return classObject;
}