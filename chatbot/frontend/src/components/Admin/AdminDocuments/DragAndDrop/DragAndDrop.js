import React, { Component } from 'react'
// Credit to https://medium.com/@650egor/simple-drag-and-drop-file-upload-in-react-2cb409d88929

/**
 * Component represents drag and drop area for files
 */
class DragAndDrop extends Component {
  // If a user is dragging on the drag and drop area then dragging will be set to true  
  state = {
        dragging: false,
      }
      
  dropRef = React.createRef()
  /*
   * Prevents default behaviour
   */
  handleDrag = (e) => {
    e.preventDefault();
    e.stopPropagation();
  }
  /*
  * Users dragging item onto screen sets dragin state to True
  */
  handleDragIn = (e) => {
    e.preventDefault();
    e.stopPropagation();
    this.dragCounter++  ;
    if (e.dataTransfer.items && e.dataTransfer.items.length > 0) {
      this.setState({dragging: true});
    }
   
  }
  /**
   * users dragging off of screen sets dragging to false 
   * and restards drag counter
   */
  handleDragOut = (e) => {
    e.preventDefault();
    e.stopPropagation();
    
    this.dragCounter--;
    if (this.dragCounter > 0) return
    this.setState({dragging: false});
  }
  /**
   * On drop transfer data
   */
  handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    this.setState({dragging: false});
    if (e.dataTransfer.files && e.dataTransfer.files.length > 0) {
      this.props.handleDrop(e.dataTransfer.files);
      e.dataTransfer.clearData();
      this.dragCounter = 0;
    }
  }
  /**
   * set up drag events when component is created
   */
  componentDidMount() {
    this.dragCounter = 0;
    let div = this.dropRef.current;
    div.addEventListener('dragenter', this.handleDragIn);
    div.addEventListener('dragleave', this.handleDragOut);
    div.addEventListener('dragover', this.handleDrag);
    div.addEventListener('drop', this.handleDrop);
  }
  componentWillUnmount() {
    let div = this.dropRef.current
    div.removeEventListener('dragenter', this.handleDragIn);
    div.removeEventListener('dragleave', this.handleDragOut);
    div.removeEventListener('dragover', this.handleDrag);
    div.removeEventListener('drop', this.handleDrop);
  }
  render() {
    return (
        <div
        style={{display: 'inline-block', position: 'relative'}}
        ref={this.dropRef}
      >
        {this.state.dragging &&
          <div 
            style={{
              border: 'dashed grey 4px',
              backgroundColor: 'rgba(255,255,255,.8)',
              position: 'absolute',
              top: 0,
              bottom: 0,
              left: 0, 
              right: 0,
              zIndex: 9999
            }}
          >
            <div 
              style={{
                
                top: '50%',
                right: 0,
                left: 0,
                textAlign: 'center',
                color: 'grey',
                fontSize: 36
              }}
            >
              <div>drop here</div>
            </div>
          </div>
        }
        <ul>

       
        {this.props.children}
        </ul>
      </div>
    )
  }
}
export default DragAndDrop