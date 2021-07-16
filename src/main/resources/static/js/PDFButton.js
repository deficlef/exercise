'use strict';

const e = React.createElement;

class ExportPDFButton extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (
            <button type="button" class="success button" onClick={() => {
                let reportPageHeight = $("body").innerHeight();
                let reportPageWidth = $("body").innerWidth();

                let pdfCanvas = $('<canvas />').attr({
                    id: "canvaspdf",
                    width: reportPageWidth,
                    height: reportPageHeight
                });

                let pdfctx = $(pdfCanvas)[0].getContext('2d');
                let pdfctxX = 0;
                let pdfctxY = 0;
                let buffer = 100;

                $("canvas").each(function (index) {
                    let canvasHeight = $(this).innerHeight();
                    let canvasWidth = $(this).innerWidth();

                    pdfctx.drawImage($(this)[0], pdfctxX, pdfctxY, canvasWidth, canvasHeight);
                    pdfctxX += canvasWidth + buffer;

                    if (index % 2 === 1) {
                        pdfctxX = 0;
                        pdfctxY += canvasHeight + buffer;
                    }
                });
                let leftOffest = 0.25 * reportPageWidth;
                let pdf = new jsPDF('l', 'pt', [reportPageWidth, reportPageHeight]);
                pdf.addImage($(pdfCanvas)[0], 'PNG', leftOffest, 50);
                html2canvas($('#table_section')[0]).then(canvas => {
                    pdf.addImage(canvas, 'PNG', leftOffest, $("#barChart").innerHeight());
                    //TODO: Experiencing an error on trying to render the flags as part of the canvas.
                    // DOMException: Failed to execute 'toDataURL' on 'HTMLCanvasElement': Tainted canvases may not be exported.
                    // Might not have time to fix so skipping this.
                    // document.body.appendChild(canvas)
                    window.open(URL.createObjectURL(pdf.output("blob")));
                });
            }}>
                Generate PDF
            </button>
        );
    }
}

ReactDOM.render(e(ExportPDFButton), document.querySelector('#export_pdf_button_container'));