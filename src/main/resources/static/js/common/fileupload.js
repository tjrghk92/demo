
import React, { Component, Fragment} from 'react';
import '../../css/common/fileupload.css';

class fileupload extends Component {
    constructor(props) {
        super(props);

        this.state = {
           file : null
        };

		this.fileChange = this.fileChange.bind(this);
    }
	
	componentDidMount() {
		this.fileChange();
	}
    fileChange() {
		let maxSize = this.props.maxSize;
		let extns = this.props.extns;
		let maxFile = this.props.maxFile;

		const change = (e) =>{
			let obj = e.target;
			if(typeof obj.files[0] !== "undefined"){
				let mbConvert = maxSize /1024 /1024
				let sizeMb = mbConvert + ((mbConvert%1 > 0.5) ? (1-(mbConvert %1)) %1 : -(mbConvert %1))
				
				let fileSize = obj.files[0].size;
				let fileValue = obj.value.split("\\")
				let fileName = fileValue[fileValue.length-1]; 
			
				let clone;

				if(fileSize > maxSize ){
					alert(sizeMb + "MB이하의 파일만 등록해주세요.");
					obj.value = "";
					if (/(MSIE|Trident)/.test(navigator.userAgent)) { 
						clone = obj.cloneNode();
						obj.replaceWith(clone);
					}
				}else{
					if(extns.indexOf(fileName.substring(fileName.lastIndexOf('.'), fileName.length).toLowerCase().split('.')[1]) === -1){
						alert("지원하지 않는 파일확장자입니다.");
						obj.value = "";
						if (/(MSIE|Trident)/.test(navigator.userAgent)) { 
							clone = obj.cloneNode();
							obj.replaceWith(clone);
						}
					}else{
						if(maxFile <= document.querySelector(".fileArea").childElementCount){
							alert(maxFile + "개 파일 이하만 등록해주세요.");
							obj.value = "";
							if (/(MSIE|Trident)/.test(navigator.userAgent)) { 
								clone = obj.cloneNode();
								obj.replaceWith(clone);
							}
						}else{
							clone = obj.cloneNode();
							clone.value = "";
							
							let count = document.querySelector(".fileArea").childElementCount;
							obj.name = 'atchFileImg' + count;
							obj.className = 'atchFileImg' + count;

							document.querySelector(".tempArea").appendChild(clone);
							document.querySelector(".fileArea").appendChild(obj);

							let btn = document.createElement("p");
							btn.className = "tempBtn"
							btn.classList.add('atchFileImg' + count);
							btn.innerHTML = fileName + "<a href='#' class='delBtn'>x</a>";
							document.querySelector(".btnArea").appendChild(btn);
						
							let reader = new FileReader();
							
							let img = document.createElement("img");
							reader.onloadend = () => {
								img.src = reader.result;
								img.className = "tempImg";
								img.classList.add('atchFileImg' + count);
							}
							reader.readAsDataURL(obj.files[0]);

							document.querySelector(".previewArea").appendChild(img);
						}
					}
				}
			}
		}

		document.addEventListener('change', function(e){
			if(e.target.classList.contains('tempFile')){
				change(e)
			}
		});

		document.addEventListener('click', function(e){
			let delBtn = e.target;
			if(delBtn.className.indexOf('delBtn') > -1){
				console.log(e.target.parentNode.classList)
				let header = document.querySelectorAll('.' + delBtn.parentNode.classList[1]);
				header.forEach((value) => {
					value.parentNode.removeChild(value);
				});
			}
		});
			
	}

    render() {
		
        return (
			<Fragment>
				<div className="btnArea">
				</div>
				<div className="tempArea">
					<input type="file" name="tempFile"  className="tempFile"/>
				</div>
				<div className="fileArea">
				</div>
				<div className="previewArea">
				</div>
			</Fragment>
        );
    }
}


export default fileupload;

